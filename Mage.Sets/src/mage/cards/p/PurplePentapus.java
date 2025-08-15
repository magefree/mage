package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PurplePentapus extends CardImpl {

    public PurplePentapus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.OCTOPUS);
        this.subtype.add(SubType.STARFISH);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When this creature enters, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1)));

        // {2}{B}, Tap an untapped creature you control: Return this card from your graveyard to the battlefield tapped.
        Ability ability = new SimpleActivatedAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToBattlefieldEffect(true), new ManaCostsImpl<>("{2}{B}")
        );
        ability.addCost(new TapTargetCost(StaticFilters.FILTER_CONTROLLED_UNTAPPED_CREATURE));
        this.addAbility(ability);
    }

    private PurplePentapus(final PurplePentapus card) {
        super(card);
    }

    @Override
    public PurplePentapus copy() {
        return new PurplePentapus(this);
    }
}
