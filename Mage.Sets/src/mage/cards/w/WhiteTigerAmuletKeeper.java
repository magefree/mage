package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class WhiteTigerAmuletKeeper extends CardImpl {

    public WhiteTigerAmuletKeeper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // {3}{G}, Exile this card from your graveyard: Draw a card. You may put a land card from your hand onto the battlefield.
        Ability ability = new SimpleActivatedAbility(
            Zone.GRAVEYARD,
            new DrawCardSourceControllerEffect(1),
            new ManaCostsImpl<>("{3}{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        ability.addEffect(new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A));
        this.addAbility(ability);
    }

    private WhiteTigerAmuletKeeper(final WhiteTigerAmuletKeeper card) {
        super(card);
    }

    @Override
    public WhiteTigerAmuletKeeper copy() {
        return new WhiteTigerAmuletKeeper(this);
    }
}
