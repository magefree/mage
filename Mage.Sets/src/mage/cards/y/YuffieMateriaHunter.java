package mage.cards.y;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.NinjutsuAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class YuffieMateriaHunter extends CardImpl {

    public YuffieMateriaHunter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NINJA);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Ninjutsu {1}{R}
        this.addAbility(new NinjutsuAbility("{1}{R}"));

        // When Yuffie enters, gain control of target noncreature artifact for as long as you control Yuffie. Then you may attach an Equipment you control to Yuffie.
        Ability ability = new EntersBattlefieldTriggeredAbility(new GainControlTargetEffect(Duration.WhileControlled));
        ability.addEffect(new YuffieMateriaHunterEffect());
        ability.addTarget(new TargetPermanent(StaticFilters.FILTER_ARTIFACT_NON_CREATURE));
        this.addAbility(ability);
    }

    private YuffieMateriaHunter(final YuffieMateriaHunter card) {
        super(card);
    }

    @Override
    public YuffieMateriaHunter copy() {
        return new YuffieMateriaHunter(this);
    }
}

class YuffieMateriaHunterEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledPermanent(SubType.EQUIPMENT);

    YuffieMateriaHunterEffect() {
        super(Outcome.Benefit);
        staticText = "Then you may attach an Equipment you control to {this}";
    }

    private YuffieMateriaHunterEffect(final YuffieMateriaHunterEffect effect) {
        super(effect);
    }

    @Override
    public YuffieMateriaHunterEffect copy() {
        return new YuffieMateriaHunterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (player == null || permanent == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(0, 1, filter, true);
        player.choose(outcome, target, source, game);
        return permanent.addAttachment(target.getFirstTarget(), source, game);
    }
}
