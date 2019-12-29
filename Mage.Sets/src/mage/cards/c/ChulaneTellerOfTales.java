package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutCardFromHandOntoBattlefieldEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChulaneTellerOfTales extends CardImpl {

    public ChulaneTellerOfTales(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}{U}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast a creature spell, draw a card, then you may put a land card from your hand onto the battlefield.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new ChulaneTellerOfTalesEffect(), StaticFilters.FILTER_SPELL_A_CREATURE, false
        ));

        // {3}, {T}: Return target creature you control to its owner's hand.
        Ability ability = new SimpleActivatedAbility(new ReturnToHandTargetEffect(), new GenericManaCost(3));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private ChulaneTellerOfTales(final ChulaneTellerOfTales card) {
        super(card);
    }

    @Override
    public ChulaneTellerOfTales copy() {
        return new ChulaneTellerOfTales(this);
    }
}

class ChulaneTellerOfTalesEffect extends OneShotEffect {
    private static final Effect effect1 = new DrawCardSourceControllerEffect(1);
    private static final Effect effect2 = new PutCardFromHandOntoBattlefieldEffect(StaticFilters.FILTER_CARD_LAND_A);

    ChulaneTellerOfTalesEffect() {
        super(Outcome.Benefit);
        staticText = "draw a card, then you may put a land card from your hand onto the battlefield";
    }

    private ChulaneTellerOfTalesEffect(final ChulaneTellerOfTalesEffect effect) {
        super(effect);
    }

    @Override
    public ChulaneTellerOfTalesEffect copy() {
        return new ChulaneTellerOfTalesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        effect1.apply(game, source);
        effect2.apply(game, source);
        return true;
    }
}