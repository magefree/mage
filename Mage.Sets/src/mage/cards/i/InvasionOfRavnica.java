package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfRavnica extends CardImpl {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls that isn't exactly two colors");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(InvasionOfRavnicaPredicate.instance);
    }

    public InvasionOfRavnica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.BATTLE}, "{5}");

        this.subtype.add(SubType.SIEGE);
        this.setStartingDefense(4);
        this.secondSideCardClazz = mage.cards.g.GuildpactParagon.class;

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.addAbility(new SiegeAbility());

        // When Invasion of Ravnica enters the battlefield, exile target nonland permanent an opponent controls that isn't exactly two colors.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private InvasionOfRavnica(final InvasionOfRavnica card) {
        super(card);
    }

    @Override
    public InvasionOfRavnica copy() {
        return new InvasionOfRavnica(this);
    }
}

enum InvasionOfRavnicaPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getColor(game).getColorCount() != 2;
    }
}
