package mage.cards.r;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SourcePermanentPowerValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.ExhaustAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.SimpleManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.abilities.mana.builder.common.ActivatedAbilityManaBuilder;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.choices.ChoiceColor;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInHand;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RedshiftRocketeerChief extends CardImpl {

    public RedshiftRocketeerChief(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}: Add X mana of any one color, where X is Redshift's power. Spend this mana only to activate abilities.
        this.addAbility(new SimpleManaAbility(new RedshiftRocketeerChiefManaEffect(), new TapSourceCost()));

        // Exhaust -- {10}{R}{G}: Put any number of permanent cards from your hand onto the battlefield.
        this.addAbility(new ExhaustAbility(new RedshiftRocketeerChiefEffect(), new ManaCostsImpl<>("{10}{R}{G}")));
    }

    private RedshiftRocketeerChief(final RedshiftRocketeerChief card) {
        super(card);
    }

    @Override
    public RedshiftRocketeerChief copy() {
        return new RedshiftRocketeerChief(this);
    }
}

class RedshiftRocketeerChiefManaEffect extends ManaEffect {

    private final ConditionalManaBuilder manaBuilder = new ActivatedAbilityManaBuilder();

    RedshiftRocketeerChiefManaEffect() {
        this.staticText = "Add X mana of any one color, where X is {this}'s power. " + manaBuilder.getRule();
    }

    private RedshiftRocketeerChiefManaEffect(final RedshiftRocketeerChiefManaEffect effect) {
        super(effect);
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        if (game == null) {
            return netMana;
        }
        int currentPower = SourcePermanentPowerValue.NOT_NEGATIVE.calculate(game, source, null);
        netMana.add(manaBuilder.setMana(Mana.BlackMana(currentPower), source, game).build());
        netMana.add(manaBuilder.setMana(Mana.BlueMana(currentPower), source, game).build());
        netMana.add(manaBuilder.setMana(Mana.RedMana(currentPower), source, game).build());
        netMana.add(manaBuilder.setMana(Mana.GreenMana(currentPower), source, game).build());
        netMana.add(manaBuilder.setMana(Mana.WhiteMana(currentPower), source, game).build());
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return mana;
        }
        ChoiceColor choice = new ChoiceColor();
        if (!controller.choose(Outcome.PutManaInPool, choice, game)) {
            return mana;
        }
        Mana chosen = choice.getMana(SourcePermanentPowerValue.NOT_NEGATIVE.calculate(game, source, null));
        return manaBuilder.setMana(chosen, source, game).build();
    }

    @Override
    public RedshiftRocketeerChiefManaEffect copy() {
        return new RedshiftRocketeerChiefManaEffect(this);
    }
}

class RedshiftRocketeerChiefEffect extends OneShotEffect {

    RedshiftRocketeerChiefEffect() {
        super(Outcome.Benefit);
        staticText = "put any number of permanent cards from your hand onto the battlefield";
    }

    private RedshiftRocketeerChiefEffect(final RedshiftRocketeerChiefEffect effect) {
        super(effect);
    }

    @Override
    public RedshiftRocketeerChiefEffect copy() {
        return new RedshiftRocketeerChiefEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        TargetCard target = new TargetCardInHand(0, Integer.MAX_VALUE, StaticFilters.FILTER_CARD_PERMANENTS);
        player.choose(outcome, player.getHand(), target, source, game);
        Cards cards = new CardsImpl(target.getTargets());
        return !cards.isEmpty() && player.moveCards(cards, Zone.BATTLEFIELD, source, game);
    }
}
