package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.SkipNextPlayerUntapStepEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.TargetPlayer;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author LevelX
 */
public final class YoseiTheMorningStar extends CardImpl {

    public YoseiTheMorningStar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DRAGON);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Yosei, the Morning Star dies, target player skips their next untap step. Tap up to five target permanents that player controls.
        Ability ability = new DiesSourceTriggeredAbility(new SkipNextPlayerUntapStepEffect("target"));
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new YoseiTheMorningStarTarget());
        ability.addEffect(new TapTargetEffect().setTargetPointer(new SecondTargetPointer()));
        this.addAbility(ability);
    }

    private YoseiTheMorningStar(final YoseiTheMorningStar card) {
        super(card);
    }

    @Override
    public YoseiTheMorningStar copy() {
        return new YoseiTheMorningStar(this);
    }
}

class YoseiTheMorningStarTarget extends TargetPermanent {

    private static final FilterPermanent filterTemplate = new FilterPermanent("permanents that player controls");

    public YoseiTheMorningStarTarget() {
        super(0, 5, filterTemplate, false);
    }

    public YoseiTheMorningStarTarget(final YoseiTheMorningStarTarget target) {
        super(target);
    }

    @Override
    public boolean canTarget(UUID controllerId, UUID id, Ability source, Game game) {
        Player player = game.getPlayer(source.getFirstTarget());
        if (player != null) {
            this.filter = filterTemplate.copy();
            this.filter.add(new ControllerIdPredicate(player.getId()));
            return super.canTarget(controllerId, id, source, game);
        }
        return false;
    }

    @Override
    public YoseiTheMorningStarTarget copy() {
        return new YoseiTheMorningStarTarget(this);
    }
}
