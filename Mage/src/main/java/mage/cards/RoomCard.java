package mage.cards;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.condition.common.RoomLeftHalfLockedCondition;
import mage.abilities.condition.common.RoomRightHalfLockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RoomManaValueEffect;
import mage.abilities.effects.common.RoomNameEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;

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

        leftHalfCard = new RoomCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesLeft, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new RoomCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesRight, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
    }

    protected RoomCard(RoomCard card) {
        super(card);
        this.leftDoorAbility = card.leftDoorAbility;
        this.rightDoorAbility = card.rightDoorAbility;
    }

    protected void AddRoomAbilities(Ability leftAbility, Ability rightAbility) {
        this.leftDoorAbility = leftAbility;
        this.rightDoorAbility = rightAbility;

        getLeftHalfCard().addAbility(leftAbility);
        getRightHalfCard().addAbility(rightAbility);
        
        if (leftAbility != null) {
            this.addAbility(leftAbility.setRuleAtTheTop(false));
        }
        if (rightAbility != null) {
            this.addAbility(rightAbility.setRuleAtTheTop(false));
        }

        // Add the one-shot effect to unlock a door on cast -> ETB
        Ability entersAbility = new EntersBattlefieldAbility(new RoomEnterUnlockEffect());
        entersAbility.setRuleVisible(false);
        this.addAbility(entersAbility);

        // Add abilities to remove locked door abilities
        if (leftAbility != null && !(leftAbility instanceof UnlockThisDoorTriggeredAbility)) {
            Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                    new LoseAbilitySourceEffect(leftAbility, Duration.WhileOnBattlefield),
                    RoomLeftHalfLockedCondition.instance, "")).setRuleVisible(false);
            this.addAbility(ability);
        }

        if (rightAbility != null && !(rightAbility instanceof UnlockThisDoorTriggeredAbility)) {
            Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                    new LoseAbilitySourceEffect(rightAbility, Duration.WhileOnBattlefield),
                    RoomRightHalfLockedCondition.instance, "")).setRuleVisible(false);
            this.addAbility(ability);
        }

        // Add the Special Action to unlock doors.
        // These will ONLY be active if the corresponding half is LOCKED!
        if (leftAbility != null) {
            ManaCosts leftHalfManaCost = null;
            if (this.getLeftHalfCard() != null && this.getLeftHalfCard().getSpellAbility() != null) {
                leftHalfManaCost = this.getLeftHalfCard().getSpellAbility().getManaCosts();
            }
            RoomUnlockAbility leftUnlockAbility = new RoomUnlockAbility(leftHalfManaCost, true);
            this.addAbility(leftUnlockAbility.setRuleAtTheTop(true));
        }

        if (rightAbility != null) {
            ManaCosts rightHalfManaCost = null;
            if (this.getRightHalfCard() != null && this.getRightHalfCard().getSpellAbility() != null) {
                rightHalfManaCost = this.getRightHalfCard().getSpellAbility().getManaCosts();
            }
            RoomUnlockAbility rightUnlockAbility = new RoomUnlockAbility(rightHalfManaCost, false);
            this.addAbility(rightUnlockAbility.setRuleAtTheTop(true));
        }

        this.addAbility(new RoomAbility());
    }
}

class RoomEnterUnlockEffect extends OneShotEffect {
    public RoomEnterUnlockEffect() {
        super(Outcome.Neutral);
        staticText = "";
    }

    private RoomEnterUnlockEffect(final RoomEnterUnlockEffect effect) {
        super(effect);
    }

    @Override
    public RoomEnterUnlockEffect copy() {
        return new RoomEnterUnlockEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);

        if (permanent == null) {
            return false;
        }

        if (permanent.wasUnlockedOnCast()) {
            return false;
        }

        permanent.unlockOnCast(game);
        UUID permanentId = permanent.getId();

        SpellAbilityType lastCastHalf = (SpellAbilityType) game.getState()
                .getValue(permanentId.toString() + "_ROOM_LAST_CAST_HALF");
        game.getState().removeValue(permanentId.toString() + "_ROOM_LAST_CAST_HALF");

        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT) {
            return permanent.unlockLeftHalf(game, source);
        } else if (lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            return permanent.unlockRightHalf(game, source);
        }

        return true;
    }
}

// For the overall Room card flavor text and mana value effect.
class RoomAbility extends SimpleStaticAbility {
    public RoomAbility() {
        super(Zone.ALL, null);
        this.setRuleVisible(true);
        this.setRuleAtTheTop(true);
        this.addEffect(new RoomManaValueEffect());
        this.addEffect(new RoomNameEffect());
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