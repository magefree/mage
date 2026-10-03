package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class RuricTharBiomagus extends CardImpl {

    public RuricTharBiomagus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.OGRE);
        this.subtype.add(SubType.CRAB);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Prowess
        this.addAbility(new ProwessAbility());

        // Whenever Ruric Thar becomes the target of a spell or ability an opponent controls, draw a card.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(
            new DrawCardSourceControllerEffect(1), StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS
        ));
    }

    private RuricTharBiomagus(final RuricTharBiomagus card) {
        super(card);
    }

    @Override
    public RuricTharBiomagus copy() {
        return new RuricTharBiomagus(this);
    }
}
