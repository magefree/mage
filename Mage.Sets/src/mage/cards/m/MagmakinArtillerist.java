package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDiscardValue;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DiscardOneOrMoreCardsTriggeredAbility;
import mage.abilities.keyword.CyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MagmakinArtillerist extends CardImpl {

    public MagmakinArtillerist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you discard one or more cards, this creature deals that much damage to each opponent.
        this.addAbility(new DiscardOneOrMoreCardsTriggeredAbility(
                new DamagePlayersEffect(SavedDiscardValue.MUCH, TargetController.OPPONENT)
        ));

        // Cycling {1}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{1}{R}")));

        // When you cycle this card, it deals 1 damage to each opponent.
        this.addAbility(new CycleTriggeredAbility(new DamagePlayersEffect(
                1, TargetController.OPPONENT, "it"
        )));
    }

    private MagmakinArtillerist(final MagmakinArtillerist card) {
        super(card);
    }

    @Override
    public MagmakinArtillerist copy() {
        return new MagmakinArtillerist(this);
    }
}
