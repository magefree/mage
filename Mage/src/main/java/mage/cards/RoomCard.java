package mage.cards;

import java.util.UUID;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.common.RoomUnlockAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.UnlockThisDoorTriggeredAbility;
import mage.abilities.condition.common.RoomHalfLockedCondition;
import mage.abilities.costs.mana.ManaCosts;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.RoomCharacteristicsEffect;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.abilities.effects.common.continuous.LoseAbilitySourceEffect;

/**
 * @author oscscull
 */
public abstract class RoomCard extends SplitCard {
    private SpellAbilityType lastCastHalf = null;

    protected RoomCard(UUID ownerId, CardSetInfo setInfo, CardType[] types, String costsLeft,
            String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, costsLeft, costsRight, spellAbilityType, types);

        String[] names = setInfo.getName().split(" // ");

        leftHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                types, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new RoomCardHalfImpl(
                this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(),
                        setInfo.getRarity(), setInfo.getGraphicInfo()),
                types, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
    }

    protected RoomCard(RoomCard card) {
        super(card);
        this.lastCastHalf = card.lastCastHalf;
    }

    public SpellAbilityType getLastCastHalf() {
        return lastCastHalf;
    }

    public void setLastCastHalf(SpellAbilityType lastCastHalf) {
        this.lastCastHalf = lastCastHalf;
    }

    protected void addRoomAbilities(Ability leftAbility, Ability rightAbility) {
        getLeftHalfCard().addAbility(leftAbility);
        getRightHalfCard().addAbility(rightAbility);
        this.addAbility(leftAbility.copy());
        this.addAbility(rightAbility.copy());

        // Add the one-shot effect to unlock a door on cast -> ETB
        Ability entersAbility = new EntersBattlefieldAbility(new RoomEnterUnlockEffect());
        entersAbility.setRuleVisible(false);
        this.addAbility(entersAbility);

        // Remove locked door abilities - keeping unlock triggers (or they won't trigger
        // when unlocked)
        if (leftAbility != null && !(leftAbility instanceof UnlockThisDoorTriggeredAbility)) {
            Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                    new LoseAbilitySourceEffect(leftAbility, Duration.WhileOnBattlefield),
                    RoomHalfLockedCondition.LEFT, "")).setRuleVisible(false);
            this.addAbility(ability);
        }

        if (rightAbility != null && !(rightAbility instanceof UnlockThisDoorTriggeredAbility)) {
            Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                    new LoseAbilitySourceEffect(rightAbility, Duration.WhileOnBattlefield),
                    RoomHalfLockedCondition.RIGHT, "")).setRuleVisible(false);
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

    @Override
    public Abilities<Ability> getAbilities() {
        return this.abilities;
    }

    @Override
    public Abilities<Ability> getAbilities(Game game) {
        return this.abilities;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        super.setZone(zone, game);

        if (zone == Zone.BATTLEFIELD) {
            game.setZone(getLeftHalfCard().getId(), Zone.OUTSIDE);
            game.setZone(getRightHalfCard().getId(), Zone.OUTSIDE);
            return;
        }

        game.setZone(getLeftHalfCard().getId(), zone);
        game.setZone(getRightHalfCard().getId(), zone);
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

        if (permanent.wasRoomUnlockedOnCast()) {
            return false;
        }

        permanent.unlockRoomOnCast(game);
        RoomCard roomCard = null;
        // Get the parent card to access the lastCastHalf variable
        if (permanent instanceof PermanentToken) {
            Card mainCard = permanent.getMainCard();
            if (mainCard instanceof RoomCard) {
                roomCard = (RoomCard) mainCard;
            }
        } else {
            Card card = game.getCard(permanent.getId());
            if (card instanceof RoomCard) {
                roomCard = (RoomCard) card;
            }
        }
        if (roomCard == null) {
            return true;
        }

        SpellAbilityType lastCastHalf = roomCard.getLastCastHalf();

        if (lastCastHalf == SpellAbilityType.SPLIT_LEFT || lastCastHalf == SpellAbilityType.SPLIT_RIGHT) {
            roomCard.setLastCastHalf(null);
            return permanent.unlockDoor(game, source, lastCastHalf == SpellAbilityType.SPLIT_LEFT);
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
        this.addEffect(new RoomCharacteristicsEffect());
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