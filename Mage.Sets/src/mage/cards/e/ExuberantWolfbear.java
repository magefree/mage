package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.SetPowerToughnessTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ExuberantWolfbear extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledPermanent(SubType.HUMAN, "Human you control");

    public ExuberantWolfbear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");

        this.subtype.add(SubType.WOLF);
        this.subtype.add(SubType.BEAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever Exuberant Wolfbear attacks, you may change the base power and toughness of target Human you control to Exuberant Wolfbear's power and toughness until end of turn.
        Ability ability = new AttacksTriggeredAbility(new ExuberantWolfbearEffect(), true);
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private ExuberantWolfbear(final ExuberantWolfbear card) {
        super(card);
    }

    @Override
    public ExuberantWolfbear copy() {
        return new ExuberantWolfbear(this);
    }
}

class ExuberantWolfbearEffect extends OneShotEffect {

    ExuberantWolfbearEffect() {
        super(Outcome.Benefit);
        staticText = "you may change the base power and toughness of target Human you control " +
                "to {this}'s power and toughness until end of turn";
    }

    private ExuberantWolfbearEffect(final ExuberantWolfbearEffect effect) {
        super(effect);
    }

    @Override
    public ExuberantWolfbearEffect copy() {
        return new ExuberantWolfbearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new SetPowerToughnessTargetEffect(
                permanent.getPower().getValue(),
                permanent.getToughness().getValue(),
                Duration.EndOfTurn
        ), source);
        return true;
    }
}
