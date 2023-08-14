package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.Choice;
import mage.choices.ChoiceCardType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PippinGuardOfTheCitadel extends CardImpl {

    public PippinGuardOfTheCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HALFLING);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Ward {1}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{1}"), false));

        // {T}: Another target creature you control gains protection from the card type of your choice until end of turn.
        Ability ability = new SimpleActivatedAbility(new PippinGuardOfTheCitadelEffect(), new TapSourceCost());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE));
        this.addAbility(ability);
    }

    private PippinGuardOfTheCitadel(final PippinGuardOfTheCitadel card) {
        super(card);
    }

    @Override
    public PippinGuardOfTheCitadel copy() {
        return new PippinGuardOfTheCitadel(this);
    }
}

class PippinGuardOfTheCitadelEffect extends OneShotEffect {

    PippinGuardOfTheCitadelEffect() {
        super(Outcome.Benefit);
        staticText = "another target creature you control gains protection " +
                "from the card type of your choice until end of turn";
    }

    private PippinGuardOfTheCitadelEffect(final PippinGuardOfTheCitadelEffect effect) {
        super(effect);
    }

    @Override
    public PippinGuardOfTheCitadelEffect copy() {
        return new PippinGuardOfTheCitadelEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (player == null || permanent == null) {
            return false;
        }
        Choice choice = new ChoiceCardType();
        player.choose(outcome, choice, game);
        CardType cardType = CardType.fromString(choice.getChoice());
        FilterCard filter = new FilterCard(cardType.getPluralName().toLowerCase());
        filter.add(cardType.getPredicate());
        game.addEffect(new GainAbilityTargetEffect(new ProtectionAbility(filter))
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        return true;
    }
}
