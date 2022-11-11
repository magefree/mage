package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.LandsYouControlCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardSetInfo;
import mage.cards.MeldCard;
import mage.constants.*;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.token.custom.CreatureToken;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TitaniaGaeaIncarnate extends MeldCard {

    public TitaniaGaeaIncarnate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);
        this.color.setGreen(true);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Titania, Gaea Incarnate's power and toughness are each equal to the number of lands you control.
        this.addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(LandsYouControlCount.instance, Duration.EndOfGame)
        ));

        // When Titania, Gaea Incarnate enters the battlefield, return all land cards from your graveyard to the battlefield tapped.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TitaniaGaeaIncarnateEffect()));

        // {3}{G}: Put four +1/+1 counters on target land you control. It becomes a 0/0 Elemental creature with haste. It's still a land.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance(4)), new ManaCostsImpl<>("{3}{G}")
        );
        ability.addEffect(new BecomesCreatureTargetEffect(
                new CreatureToken(0, 0, "", SubType.ELEMENTAL)
                        .withAbility(HasteAbility.getInstance()),
                false, true, Duration.Custom
        ).setText("It becomes a 0/0 Elemental creature with haste. It's still a land."));
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_PERMANENT_LAND));
        this.addAbility(ability);
    }

    private TitaniaGaeaIncarnate(final TitaniaGaeaIncarnate card) {
        super(card);
    }

    @Override
    public TitaniaGaeaIncarnate copy() {
        return new TitaniaGaeaIncarnate(this);
    }
}

class TitaniaGaeaIncarnateEffect extends OneShotEffect {

    TitaniaGaeaIncarnateEffect() {
        super(Outcome.Benefit);
        staticText = "return all land cards from your graveyard to the battlefield tapped";
    }

    private TitaniaGaeaIncarnateEffect(final TitaniaGaeaIncarnateEffect effect) {
        super(effect);
    }

    @Override
    public TitaniaGaeaIncarnateEffect copy() {
        return new TitaniaGaeaIncarnateEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        return player != null && player.moveCards(
                player.getGraveyard().getCards(StaticFilters.FILTER_CARD_LAND, game),
                Zone.BATTLEFIELD, source, game, true, false, false, null
        );
    }
}
