# LexiAide — Product and UX Brief

This brief summarizes the three supplied project documents for a designer who needs to work independently. If a newer product decision conflicts with this summary, use the newer decision and update the Figma decision log.

## Product proposition

LexiAide is a Madagascar-focused legal-services platform intended to connect citizens, entrepreneurs, students/researchers, and legal professionals. The planned ecosystem includes:

- a mobile app as the primary experience;
- a synchronized website for discovery, legal resources, news, and account continuity;
- an in-app community/social feed to distribute accessible legal information.

The product should make legal information and professional access easier across geographic, financial, language, and connectivity barriers.

## User types and design needs

### Citizen or entrepreneur

- Wants help with a real problem, plain-language guidance, and access to a trusted professional.
- May need to write and submit a case, attach evidence, understand a fee, and know what happens next.
- Needs reassurance, clear privacy explanations, and visible progress/status.
- Example persona in the cahier des charges: Rova, an entrepreneur in Toamasina/Tamatave looking for a commercial-lease model or remote legal help.

### Student or researcher

- Needs to find laws, jurisprudence, practical articles, and books.
- Must be able to browse and read previously synchronized resources with limited or no internet.
- Needs search, categories, source/date information, offline availability, and readable long-form content.
- Example persona: Fitahiana, a law master's student in Antananarivo.

### Legal professional

- Includes lawyers, notaries, and bailiffs/huissiers.
- Needs a credible professional profile, a verification journey, relevant case discovery, notifications, and a clear way to accept or decline a contact opportunity.
- Must understand subscription/access status and what information is visible before accepting a case.
- Example persona: Maître Heri, a lawyer seeking clients beyond the capital.

Do not make the sample personas' personal details visible in public prototype content; use role-based, fictional examples.

## MVP versus full specification

The **Plan d'action** explicitly warns against building everything at once. Its MVP emphasis is:

1. a verified directory of legal professionals;
2. simple matching from a user question/problem to suitable professionals, with notifications;
3. useful legal content such as plain-language articles and practical guides.

The **Cahier des charges** describes a wider product: multilingual onboarding; offline legal resources; free document downloads; paid case filing and professional subscriptions; contact unlocking; a social feed; web/mobile synchronization; advertising; and partner content. The **Recap** adds pre-consultation, legal consultation, document drafting, and books.

Design recommendation: build the shared visual foundation and the three MVP pillars first. Keep full-spec features in clearly marked later-phase sections rather than mixing every feature into the first release. Do not remove a stated full-spec feature; show its phase as a product decision to confirm.

## Core flows to design

### Common entry and account

- Splash/entry with Français and Malagasy choices.
- Role selection: citoyen/entrepreneur, étudiant/chercheur, professionnel.
- Simple sign-up/sign-in using name, email or phone, and password.
- Professional sign-up adds a professional type and a secure document/credential submission step.
- Use reviewed privacy/terms copy supplied by the product/legal owner; do not invent legal consent language.

### Citizen and student content

- Home/search entry to legal information and professional discovery.
- Resource search by keyword/category; categories named in the specification include civil and labor law.
- Resource list and detail with document type, language, source/date, download state, and an explicit “Disponible hors ligne” badge when cached.
- Download/sync status and low-storage, stale-content, offline, and retry states.
- Free legal document templates should be clearly distinguished from personalized legal advice.

### Citizen case submission

The cahier des charges lists this sequence:

1. explain the legal problem;
2. upload supporting files;
3. review a pro-forma invoice;
4. pay via Mobile Money;
5. download the final invoice.

After validated payment, the stated case status is “En attente de représentant” and the case enters the professional catalog. Make steps, amount, file privacy, payment result, and next action explicit. The specification does not say that case submission can be completed offline; treat it as an online-only action unless confirmed otherwise.

### Professional discovery and response

- Professional profile/application and verification status.
- Subscription/access gate before detailed case-list access.
- New-case notification based on specialty.
- Case list with a minimum, anonymized summary; do not expose identity or sensitive documents before authorized acceptance.
- “Prendre contact” action and confirmation.
- Contact details unlock and a citizen notification after the professional accepts, according to the cahier des charges.
- Keep a clear distinction between platform activity and financial arrangements made outside LexiAide.

The product documents conflict on how much case detail professionals can see before accepting. Prototype the privacy-preserving option and mark the access policy “À confirmer.”

### Website and later modules

- Public landing page, product explanation, professional early-access/waitlist CTA, articles/resources, and account entry.
- Future-scope designs may include social posts with like/comment/share, act drafting, pre-consultation, books, appointment/calendar synchronization, sponsored content, and ads.
- If these are included in a handoff, label them as later-phase designs unless product management has moved them into the MVP.

## Pricing and commercial decisions — do not silently resolve

The documents state:

- **Cahier des charges:** 10,000 MGA as a one-time fixed case-submission fee.
- **Cahier des charges:** professional monthly subscriptions of 100,000 MGA for lawyers, 100,000 MGA for notaries, and 50,000 MGA for bailiffs/huissiers.
- **Recap:** “Abonnement : 10.000 MGA” under paid services, followed by pre-consultation, legal consultation, act drafting, contact, and books.

The Recap may imply a consumer subscription, but that is not established elsewhere. Do not present 10,000 MGA as both a subscription and a case fee. Use a configurable price component and mark the consumer offer, billing frequency, included benefits, and exact payment step “À confirmer.”

The specification names MVola, Orange Money, and Airtel Money as intended payment options. Design a flexible payment-method selection component; do not imply an integration or successful payment until the product owner confirms the chosen provider and payment states.

## Privacy, trust, and safety constraints

- Case narratives and uploaded evidence can contain highly sensitive personal information.
- The cahier des charges says case data must be confidential and restricts access to the case owner and the professional who accepts it.
- A list/catalog requirement says cases become visible to subscribed professionals after payment. Reconcile this with the confidentiality rule by using a redacted/anonymized listing and gating full detail; flag it for product/legal confirmation.
- Do not put case names, allegations, uploaded files, or contact details into notification previews or social content.
- Do not use fake real-world law-firm endorsements, bar association badges, or official seals.
- Madagascar privacy rules, professional secrecy, data retention, and end-to-end encryption need legal/technical validation; the Figma interface cannot itself establish compliance.

## Brand direction and limits

The documents ask for a memorable LexiAide identity that conveys trust, security, modernity, clarity, and accessibility. They suggest institutional blue, warm gold/yellow, and white as possible starting colors, with clean typography.

No approved LexiAide logo, final color values, typography, or brand guide is supplied. Present any visual identity as a proposal. Do not reuse the Ravinala Digital logo shown in the action-plan PDF as the LexiAide logo.

## Existing project state

At the time this brief was written, the repository contains a Spring Boot foundation, a health endpoint, common error handling, profile/database configuration, and placeholder package structure. There is no implemented login, business API, resource catalog, case management, payment, file storage, notification, mobile app, or web UI. Use Figma to specify the intended experience; do not imply these product features are already available.
