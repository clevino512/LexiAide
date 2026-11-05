# LexiAide — Figma Structure and Handoff

Use this when creating or extending the LexiAide Figma file. Preserve the team's existing file if one exists; adapt these page names rather than duplicating approved work.

## Suggested Figma pages

1. `00 — Brief & décisions` — scope, target role, phase, unresolved decisions, links to requirements.
2. `01 — Foundations` — brand explorations, color/type/spacing variables, grid, iconography, accessibility notes.
3. `02 — Mobile citoyen & étudiant` — onboarding, discovery, resource library, offline reading, case submission.
4. `03 — Mobile professionnel` — professional application/verification, dashboard, case discovery, response and contact.
5. `04 — Web` — landing/waitlist, resource discovery, synchronized account surfaces, desktop responsive patterns.
6. `05 — Components & states` — shared components, variants, loading/empty/error/offline/access states.
7. `06 — Prototype & handoff` — clickable user journeys, annotations, open questions, implementation references.

Use descriptive frame names such as `Citoyen / Déposer un dossier / 03 — Documents`, not `Frame 21`. Group flows by role and journey.

## Foundations and components

Create editable Figma variables/styles for semantic roles rather than embedding arbitrary values in each frame:

- color: `text/primary`, `text/secondary`, `surface/base`, `surface/raised`, `border/default`, `action/primary`, `status/success`, `status/warning`, `status/error`, `status/info`;
- typography: display/title/body/caption and legal long-form reading styles;
- spacing, radius, elevation, and touch-target conventions;
- language modes or explicit FR/MG frame variants for important content.

The proposed blue/gold/white direction is not final. Test contrast and reading comfort before selecting values. Do not use color alone for verification, payment, or offline status.

Build reusable components/variants for:

- buttons: primary, secondary, quiet, destructive, loading, disabled;
- form fields: default, focus, error, helper text, password visibility, phone/email;
- app bars, navigation, bottom tabs, breadcrumbs, alerts, banners, dialogs, toasts;
- professional cards, verification badges, specialties, location, contact states;
- resource cards, category chips, offline/download/sync badges, document metadata;
- case cards with redacted and detail-gated variants, status chips, attachment rows;
- invoice/price summaries, payment-method rows, payment-progress and result states;
- empty/loading/error/offline/retry states and pagination or “load more” patterns.

Use Figma Auto Layout, component properties, variants, constraints, and sensible layer names. Components must remain editable; do not flatten UI frames into images.

## Responsive and language coverage

Design mobile first. Use representative frames around 360×800 and 390×844, then check tablet/web layouts around 768px and 1440px. Match the actual product team's target devices if specified later.

- Use Auto Layout and resizing constraints; do not assume every label has the same length in French and Malagasy.
- Keep translations as editable text layers. Make explicit FR and MG variants for critical journeys.
- Ensure Malagasy diacritics render in the selected font. Ask for human review of Malagasy legal terminology.
- Test long names, multi-line legal titles, large file names, long validation messages, and large currency amounts.
- Desktop should use available width for comparison and work management, not simply scale a phone frame.

## States to include

Include relevant states for each flow, not only polished success frames:

| Area | Required states |
|---|---|
| Network | online, offline, reconnecting, failed retry |
| Resources | not downloaded, downloading, available offline, outdated, low storage |
| Account | signed out, validation error, role selected, pending professional review, approved, rejected |
| Case | draft, upload progress/failure, awaiting payment, payment failed/pending/success, awaiting representative, accepted/declined/closed |
| Professional access | no active subscription, active subscription, case summary redacted, contact locked, contact unlocked |
| Forms | empty, invalid, saved, interrupted/resumed |
| Notifications | received, read, privacy-safe preview, no notifications |

Do not design “submit while offline” as a committed backend feature. If a useful offline draft is proposed, label it as a design recommendation requiring product confirmation.

## Prototype requirements

Prototype at least:

1. **Citizen/student:** choose language and role → find/read or save a legal resource offline.
2. **Citizen:** start a case → describe the issue → add files → review charges → choose a payment method → see a result and next step. Use neutral price notes until the commercial decision is approved.
3. **Professional:** complete verification → receive a case notification → view an anonymized summary → see the subscription/access condition → accept/decline or request contact.
4. **Web:** land on LexiAide → understand the service → reach resources or the professional early-access form.

Connect primary and secondary actions, back navigation, errors, and retry. Use annotations for behavior that a prototype cannot enforce, such as access control, payment confirmation, synchronization, or encryption.

## Privacy-safe sample data

- Use clearly fictional names and neutral, invented case descriptions.
- Do not copy personal or sensitive case details from real documents, screenshots, or user records.
- Keep professional contact details hidden until the specified contact-unlock event.
- Use generic file names and synthetic previews; avoid exposing document contents.
- Keep lock/permission state visible but do not use alarmist or absolute security language.

## Annotation format

Attach a concise note near the affected flow:

```text
Decision: À confirmer — visibilité du dossier avant acceptation
Source: Cahier des charges, confidentialité + catalogue professionnel
Proposition UI: résumé anonymisé, pièces et coordonnées masquées
Impact: droits d'accès, notifications, contenu des cartes de cas
```

For an implementation annotation, state trigger, result, error state, and access rule. Example:

```text
Trigger: sélection d'un document hors ligne
Success: badge "Disponible hors ligne" + date de synchronisation
Failure: message court + action Réessayer
Access: lecture des contenus déjà téléchargés seulement
```

## Final handoff checklist

- [ ] MVP and later-phase scope are labeled and not mixed silently.
- [ ] Every main flow has an entry, success state, and recovery/empty state.
- [ ] Role permissions are visible; private case details and contacts are not exposed prematurely.
- [ ] FR/MG variants are editable and checked for clipping/overflow.
- [ ] Offline availability, freshness, sync, and online-only actions are distinguishable.
- [ ] Price labels say one-time or monthly and unresolved prices are flagged.
- [ ] Components use variants and shared styles/variables where appropriate.
- [ ] The prototype links work and main flows can be completed without hidden jumps.
- [ ] Legal, privacy, verification, payment, and security assumptions are listed for product/legal review.
- [ ] Handoff notes explain responsive rules, component behavior, and any non-obvious states.
