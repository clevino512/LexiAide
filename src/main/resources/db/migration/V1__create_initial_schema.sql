-- LexiAide - Sch?ma initial de base de donn?es PostgreSQL (Flyway V1)

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS "citext";

-- 1. Table : users
CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email CITEXT UNIQUE,
    phone VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255),
    photo_url TEXT,
    preferred_language VARCHAR(10),
    status VARCHAR(50) DEFAULT 'PENDING',
    email_verified_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 2. Table : roles
CREATE TABLE IF NOT EXISTS roles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

-- 3. Table : user_roles (Cl? primaire compos?e : user_id, role_id)
CREATE TABLE IF NOT EXISTS user_roles (
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    role_id UUID NOT NULL REFERENCES roles(id) ON DELETE CASCADE,
    assigned_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (user_id, role_id)
);

-- 4. Table : user_sessions
CREATE TABLE IF NOT EXISTS user_sessions (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID REFERENCES users(id) ON DELETE CASCADE,
    refresh_token_hash VARCHAR(255) UNIQUE,
    device_id VARCHAR(100),
    ip_address INET,
    expires_at TIMESTAMPTZ,
    revoked_at TIMESTAMPTZ
);

-- 5. Table : student_profiles
CREATE TABLE IF NOT EXISTS student_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    university VARCHAR(255),
    field_of_study VARCHAR(255),
    study_level VARCHAR(100),
    bio TEXT
);

-- 6. Table : professional_types
CREATE TABLE IF NOT EXISTS professional_types (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code VARCHAR(50) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL
);

-- 7. Table : professional_profiles
CREATE TABLE IF NOT EXISTS professional_profiles (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL UNIQUE REFERENCES users(id) ON DELETE CASCADE,
    professional_type_id UUID REFERENCES professional_types(id) ON DELETE SET NULL,
    license_number VARCHAR(100) UNIQUE,
    office_name VARCHAR(255),
    city VARCHAR(100),
    office_address TEXT,
    bio TEXT,
    verification_status VARCHAR(50) DEFAULT 'PENDING'
);

-- 8. Table : professional_documents
CREATE TABLE IF NOT EXISTS professional_documents (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    professional_id UUID NOT NULL REFERENCES professional_profiles(id) ON DELETE CASCADE,
    document_type VARCHAR(100),
    file_url TEXT,
    mime_type VARCHAR(100),
    status VARCHAR(50) DEFAULT 'PENDING',
    uploaded_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);

-- 9. Table : professional_verifications
CREATE TABLE IF NOT EXISTS professional_verifications (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    professional_id UUID NOT NULL REFERENCES professional_profiles(id) ON DELETE CASCADE,
    reviewed_by UUID REFERENCES users(id) ON DELETE SET NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    rejection_reason TEXT,
    submitted_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    reviewed_at TIMESTAMPTZ
);

-- Index pour optimiser les recherches fr?quentes
CREATE INDEX IF NOT EXISTS idx_professional_profiles_user_id ON professional_profiles(user_id);
CREATE INDEX IF NOT EXISTS idx_professional_profiles_type_id ON professional_profiles(professional_type_id);
CREATE INDEX IF NOT EXISTS idx_professional_documents_prof_id ON professional_documents(professional_id);
CREATE INDEX IF NOT EXISTS idx_professional_verifications_prof_id ON professional_verifications(professional_id);
