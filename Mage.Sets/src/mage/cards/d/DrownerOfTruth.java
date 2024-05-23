package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DevoidAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EldraziSpawnToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DrownerOfTruth extends ModalDoubleFacedCard {

    public DrownerOfTruth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI}, "{5}{G/U}{G/U}",
                "Drowned Jungle", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Drowner of Truth
        // Creature — Eldrazi
        this.getLeftHalfCard().setPT(new MageInt(7), new MageInt(6));

        // Devoid
        this.getLeftHalfCard().addAbility(new DevoidAbility(this.color));

        // When you cast this spell, if {C} was spent to cast it, create two 0/1 colorless Eldrazi Spawn creature tokens with "Sacrifice this creature: Add {C}."
        Ability ability = new ConditionalInterveningIfTriggeredAbility(
                new CastSourceTriggeredAbility(new CreateTokenEffect(new EldraziSpawnToken(), 2)),
                ManaWasSpentCondition.COLORLESS,
                "When you cast this spell, if {C} was spent to cast it, "
                        + "create two 0/1 colorless Eldrazi Spawn creature tokens with \"Sacrifice this creature: Add {C}.");
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Drowned Jungle
        // Land

        // Drowned Jungle enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G} or {U}.
        this.getRightHalfCard().addAbility(new GreenManaAbility());
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private DrownerOfTruth(final DrownerOfTruth card) {
        super(card);
    }

    @Override
    public DrownerOfTruth copy() {
        return new DrownerOfTruth(this);
    }
}
