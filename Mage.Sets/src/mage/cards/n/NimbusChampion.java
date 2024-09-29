
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterTeamPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class NimbusChampion extends CardImpl {

    public NimbusChampion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Nimbus Champion attacks, you may return target creature to its owner's hand if that creature's power is less than or equal to the number of Warriors your team control.
        Ability ability = new AttacksTriggeredAbility(new NimbusChampionEffect(), true);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private NimbusChampion(final NimbusChampion card) {
        super(card);
    }

    @Override
    public NimbusChampion copy() {
        return new NimbusChampion(this);
    }
}

class NimbusChampionEffect extends OneShotEffect {

    private static final FilterTeamPermanent filter = new FilterTeamPermanent(SubType.WARRIOR, "Warriors your team controls");

    NimbusChampionEffect() {
        super(Outcome.Benefit);
        this.staticText = "return target creature to its owner's hand if "
                + "that creature's power is less than or equal to "
                + "the number of Warriors your team controls";
    }

    private NimbusChampionEffect(final NimbusChampionEffect effect) {
        super(effect);
    }

    @Override
    public NimbusChampionEffect copy() {
        return new NimbusChampionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature == null || creature.getPower().getValue()
                > new PermanentsOnBattlefieldCount(filter).calculate(game, source, this)) {
            return false;
        }
        return new ReturnToHandTargetEffect().apply(game, source);
    }
}
