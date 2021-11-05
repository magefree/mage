package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.costs.common.ExileXFromYourGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.costs.mana.VariableManaCost;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LudevicNecrogenius extends CardImpl {

    public LudevicNecrogenius(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.secondSideCardClazz = mage.cards.o.OlagLudevicsHubris.class;

        // Whenever Ludevic, Necrogenius enters the battlefield or attacks, mill a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new MillCardsControllerEffect(1)));

        // {X}{U}{U}{B}{B}, Exile X creature cards from your graveyard: Transform Ludevic, Necrogenius. X can't be zero. Activate only as a sorcery.
        this.addAbility(new TransformAbility());
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new TransformSourceEffect(), new ManaCostsImpl<>("{X}{U}{U}{B}{B}")
        );
        ability.addEffect(new InfoEffect("X can't be 0"));
        ability.addCost(new ExileXFromYourGraveCost(StaticFilters.FILTER_CARD_CREATURES_YOUR_GRAVEYARD));
        CardUtil.castStream(ability.getCosts().stream(), VariableManaCost.class).forEach(cost -> cost.setMinX(1));
        this.addAbility(ability);
    }

    private LudevicNecrogenius(final LudevicNecrogenius card) {
        super(card);
    }

    @Override
    public LudevicNecrogenius copy() {
        return new LudevicNecrogenius(this);
    }
}
