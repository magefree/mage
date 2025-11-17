package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.EntersBattlefieldThisOrAnotherTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AllyToken;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SokkaAndSuki extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.ALLY, "Ally");

    public SokkaAndSuki(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever Sokka and Suki or another Ally you control enters, attach up to one target Equipment you control to that creature.
        Ability ability = new EntersBattlefieldThisOrAnotherTriggeredAbility(
                new SokkaAndSukiEffect(), filter, false, SetTargetPointer.PERMANENT, true
        );
        ability.addTarget(new TargetPermanent(
                0, 1, StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT
        ));
        this.addAbility(ability);

        // Whenever an Equipment you control enters, create a 1/1 white Ally creature token.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new AllyToken()), StaticFilters.FILTER_CONTROLLED_PERMANENT_EQUIPMENT
        ));
    }

    private SokkaAndSuki(final SokkaAndSuki card) {
        super(card);
    }

    @Override
    public SokkaAndSuki copy() {
        return new SokkaAndSuki(this);
    }
}

class SokkaAndSukiEffect extends OneShotEffect {

    SokkaAndSukiEffect() {
        super(Outcome.Benefit);
        staticText = "attach up to one target Equipment you control to that creature";
    }

    private SokkaAndSukiEffect(final SokkaAndSukiEffect effect) {
        super(effect);
    }

    @Override
    public SokkaAndSukiEffect copy() {
        return new SokkaAndSukiEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = (Permanent) getValue("permanentEnteringBattlefield");
        Permanent equipment = game.getPermanent(getTargetPointer().getFirst(game, source));
        return creature != null && equipment != null && creature.addAttachment(equipment.getId(), source, game);
    }
}
