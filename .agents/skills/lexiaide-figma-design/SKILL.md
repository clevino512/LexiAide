---
name: lexiaide-figma-design
description: Creates Figma-ready UX flows, interfaces, prototypes, and handoff materials for LexiAide's legal-services platform in Madagascar. Use when designing the mobile app or synchronized website.
---

# LexiAide Figma Design Skill

Use this skill to design LexiAide in Figma. It is a project brief and design workflow, not an instruction to implement frontend or backend code.

## Read before designing

1. Read `references/product-brief.md` for the product scope, user types, journeys, and unresolved product decisions.
2. Read `references/figma-handoff.md` for the Figma file structure, component inventory, states, and handoff checklist.
3. Review the current Figma file and preserve existing approved work. Do not replace an existing brand system without first identifying it.
4. If the source PDFs are available, use them to verify requirements. Treat the written product brief and action plan as distinct sources; do not silently resolve conflicts between them.

## Design workflow

1. **Set the scope.** Identify the role, platform, journey, and delivery phase. Start with the MVP direction in the action plan: verified professionals, straightforward matching, and useful legal content. Mark wider requirements from the specifications as “full scope” or “later” until the product owner confirms their release phase.
2. **Map the journey.** Show the entry point, user goal, steps, decision points, success state, and recovery path before polishing screens.
3. **Design mobile-first.** LexiAide's mobile app is the primary product. Design its core citizen, student, and professional journeys first, then the synchronized web experience and desktop-specific surfaces.
4. **Establish a small design system.** Define reusable color, type, spacing, status, and component styles. Explore rather than declare a final brand: the documents suggest institutional blue, warm gold/yellow, and white, but no approved LexiAide logo or final palette is supplied.
5. **Cover states, not only the happy path.** Include loading, empty, offline, stale content, validation error, permission/access gate, payment pending/failure/success, verification pending/rejected, and notification states wherever relevant.
6. **Prototype the critical paths.** Make the main buttons and branch points clickable. At minimum, prototype the citizen's path to legal information or case submission and the professional's path from a case alert to an allowed next action.
7. **Annotate unresolved decisions.** Add a visible “À confirmer” note to the affected frame and record the decision in the Figma file. Continue with a neutral/configurable design instead of inventing policy, price, or legal wording.
8. **Handoff for implementation.** Follow `references/figma-handoff.md`. Include component variants, responsive behavior, language and state variants, and notes about access control and offline behavior.

## Product-specific design rules

- **Make legal help feel clear and safe.** Use plain language, short steps, clear costs and next actions. Avoid intimidating legal jargon unless it is the exact document title or a professionally reviewed term.
- **Respect the three roles.** Citizen/entrepreneur, student/researcher, and verified legal professional have different goals and permissions. Do not force them into one undifferentiated dashboard.
- **Support French and Malagasy.** Keep text in editable text layers, allow for longer translations, and do not treat an unreviewed machine translation as approved Malagasy legal copy.
- **Design for intermittent connectivity.** Make cached legal resources distinguishable from online-only actions. Show when a document is available offline, when its content is stale, and when a connection is required.
- **Protect case confidentiality.** Do not show real or identifying case details in mock data, notification previews, public feeds, or unredacted professional catalog cards. The specifications do not fully settle what a subscribed professional may see before accepting a case; show only the minimum anonymized summary until the product owner confirms access rules.
- **Do not overclaim security or legal effect.** The brief requests strict confidentiality and end-to-end encryption, but these are not implemented or technically validated. Avoid promises such as “chiffrement de bout en bout,” “conseil certifié,” or guaranteed representation unless approved by the legal/product owner.
- **Use professional verification carefully.** Reserve “Vérifié” for a real review process. Show pending, accepted, and rejected verification states without implying endorsement by a bar, notary, or government body unless that relationship is confirmed.
- **Keep advertising out of sensitive flows.** The brief allows advertising in free legal-resource/news areas and explicitly excludes paid case-submission flows. Do not put ads on case forms, private case details, payment, or invoice screens.
- **Treat money as a decision, not decoration.** Use MGA, label one-time fees versus monthly subscriptions, and do not invent a consumer subscription or payment-provider status. See the pricing conflict in the product brief.
- **Use neutral, respectful imagery.** Do not use gavels, courtrooms, seals, or national/legal symbols as if they were official LexiAide credentials. The Ravinala Digital mark visible in the action-plan PDF is the document publisher's mark, not an approved LexiAide logo.

## Required Figma output

For each assigned flow, leave a frame group that contains:

- the primary happy path and at least one important exception/recovery path;
- all relevant role, language, connectivity, and permission states;
- realistic but invented sample content with no personal or confidential data;
- a clickable prototype for the main user goal;
- a short assumption/decision note where the source documents are ambiguous;
- implementation notes for behavior that a static frame cannot communicate.

For the shared Figma file layout, components, design tokens, and final quality check, follow `references/figma-handoff.md`.
