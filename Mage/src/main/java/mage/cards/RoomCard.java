package mage.cards;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RoomManaValueEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.SubLayer;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    protected Ability leftDoorAbility;
    protected Ability rightDoorAbility;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, CardType[] typesLeft, CardType[] typesRight, String costsLeft,
            String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, CardType.mergeTypes(typesLeft, typesRight), costsLeft, costsRight, spellAbilityType);

        String[] names = setInfo.getName().split(" // ");

        leftHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesLeft, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                typesRight, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
    }

    protected RoomCard(RoomCard card) {
        super(card);
        this.leftDoorAbility = card.leftDoorAbility;
        this.rightDoorAbility = card.rightDoorAbility;
    }

    protected void AddRoomAbilities(Ability leftAbility, Ability rightAbility) {
        // Add the one-shot effect to unlock a door on cast -> etb
        Ability entersAbility = new EntersBattlefieldAbility(new RoomEnterUnlockEffect(leftAbility, rightAbility));
        entersAbility.setRuleVisible(false);
        this.addAbility(entersAbility);

        // Add the room ability with unlock abilities
        this.addAbility(new RoomAbility(this, leftAbility, rightAbility));
    }
}

class RoomEnterUnlockEffect extends OneShotEffect {
    protected Ability leftDoorAbility;
    protected Ability rightDoorAbility;

    public RoomEnterUnlockEffect(Ability leftDoorAbility,
            Ability rightDoorAbility) {
        super(Outcome.Neutral);
        staticText = "";
        this.leftDoorAbility = leftDoorAbility;
        this.rightDoorAbility = rightDoorAbility;
    }

    private RoomEnterUnlockEffect(final RoomEnterUnlockEffect effect) {
        super(effect);
        this.leftDoorAbility = effect.leftDoorAbility;
        this.rightDoorAbility = effect.rightDoorAbility;

    }

    @Override
    public RoomEnterUnlockEffect copy() {
        return new RoomEnterUnlockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        // Rule 709.5d: A permanent with a shared type line is given the appropriate
        // unlocked designation as it enters the battlefield if that half was cast as a
        // spell
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null || permanent.wasUnlockedOnCast()) {
            return false;
        }

        permanent.unlockOnCast(game);
        UUID permanentId = permanent.getId();

        SpellAbilityType lastCastHalf = (SpellAbilityType) game.getState()
                .getValue(permanentId + "_ROOM_LAST_CAST_HALF");

        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT) {
            permanent.addAbility(this.leftDoorAbility, source.getSourceId(), game);
            return permanent.unlockLeftHalf(game, source);
        } else if (lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            permanent.addAbility(this.rightDoorAbility, source.getSourceId(), game);
            return permanent.unlockRightHalf(game, source);
        }

        // If neither half was cast (e.g., put into play by another effect),
        // both doors remain locked per rule 709.5d
        return true;
    }
}

class RoomAbility extends SimpleStaticAbility {
    public RoomAbility(Card card, Ability leftAbility, Ability rightAbility) {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);

        SplitCard roomCard = (SplitCard) card;
        RoomRulesTextEffect rulesTextEffect = new RoomRulesTextEffect(leftAbility, rightAbility);

        if (leftAbility != null) {
            ManaCosts leftHalfManaCost = null;
            if (roomCard.getLeftHalfCard() != null && roomCard.getLeftHalfCard().getSpellAbility() != null) {
                leftHalfManaCost = roomCard.getLeftHalfCard().getSpellAbility().getManaCosts();
            }
            GainAbilitySourceEffect restoreLeft = new GainAbilitySourceEffect(leftAbility, Duration.WhileOnBattlefield);
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(leftHalfManaCost, true, restoreLeft);
            card.addAbility(leftUnlockAbility.setRuleAtTheTop(true));
        }

        if (rightAbility != null) {
            ManaCosts rightHalfManaCost = null;
            if (roomCard.getRightHalfCard() != null && roomCard.getRightHalfCard().getSpellAbility() != null) {
                rightHalfManaCost = roomCard.getRightHalfCard().getSpellAbility().getManaCosts();
            }
            GainAbilitySourceEffect restoreRight = new GainAbilitySourceEffect(rightAbility,
                    Duration.WhileOnBattlefield);
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(rightHalfManaCost, false, restoreRight);
            card.addAbility(rightUnlockAbility.setRuleAtTheTop(true));
        }

        this.addEffect(rulesTextEffect);
        this.addEffect(new RoomManaValueEffect());
    }

    protected RoomAbility(final RoomAbility ability) {
        super(ability);
    }

    @Override
    public String getRule() {
        return "<i>(You may cast either half. That door unlocks on the battlefield. " +
                "As a sorcery, you may pay the mana cost of a locked door to unlock it.)</i>";
    }

    @Override
    public RoomAbility copy() {
        return new RoomAbility(this);
    }
}

class RoomRulesTextEffect extends ContinuousEffectImpl {
    private final String leftText;
    private final String rightText;

    public RoomRulesTextEffect(Ability leftDoorAbility, Ability rightDoorAbility) {
        super(Duration.Custom, Layer.RulesEffects, SubLayer.NA, Outcome.Benefit);
        this.leftText = leftDoorAbility != null ? leftDoorAbility.getRule() : "";
        this.rightText = rightDoorAbility != null ? rightDoorAbility.getRule() : "";
        // Initial text will be updated dynamically
        this.staticText = "";
    }

    private RoomRulesTextEffect(final RoomRulesTextEffect effect) {
        super(effect);
        this.leftText = effect.leftText;
        this.rightText = effect.rightText;
    }

    private void setText(Game game, Ability source) {
        StringBuilder sb = new StringBuilder();
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            // Show all doors when not on battlefield
            if (!leftText.isEmpty()) {
                sb.append(leftText);
            }
            if (!rightText.isEmpty()) {
                if (sb.length() > 0)
                    sb.append("\n");
                sb.append(rightText);
            }
        } else {
            // Show only unlocked doors when on battlefield
            if (permanent.isLeftHalfUnlocked() && !leftText.isEmpty()) {
                sb.append(leftText);
            }

            if (permanent.isRightHalfUnlocked() && !rightText.isEmpty()) {
                if (sb.length() > 0)
                    sb.append("\n");
                sb.append(rightText);
            }
        }

        String newText = sb.toString();
        this.staticText = newText;
    }

    @Override
    public RoomRulesTextEffect copy() {
        return new RoomRulesTextEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        setText(game, source);
        return true;
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        if (layer == Layer.RulesEffects) {
            setText(game, source);
            return true;
        }
        return false;
    }
}
