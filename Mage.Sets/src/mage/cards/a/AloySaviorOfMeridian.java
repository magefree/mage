package mage.cards.a;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.DiscoverEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AloySaviorOfMeridian extends CardImpl {

    public AloySaviorOfMeridian(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // In You, All Things Are Possible -- Whenever one or more artifact creatures you control attack, discover X, where X is the greatest power among them.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(Zone.BATTLEFIELD,
                new AloySaviorOfMeridianEffect(), 1, StaticFilters.FILTER_PERMANENT_ARTIFACT_CREATURE, true
        ).setTriggerPhrase("Whenever one or more artifact creatures you control attack, ").withFlavorWord("In You, All Things Are Possible"));
    }

    private AloySaviorOfMeridian(final AloySaviorOfMeridian card) {
        super(card);
    }

    @Override
    public AloySaviorOfMeridian copy() {
        return new AloySaviorOfMeridian(this);
    }
}

class AloySaviorOfMeridianEffect extends OneShotEffect {

    AloySaviorOfMeridianEffect() {
        super(Outcome.Benefit);
        staticText = "discover X, where X is the greatest power among them";
    }

    private AloySaviorOfMeridianEffect(final AloySaviorOfMeridianEffect effect) {
        super(effect);
    }

    @Override
    public AloySaviorOfMeridianEffect copy() {
        return new AloySaviorOfMeridianEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        int power = this
                .getTargetPointer()
                .getTargets(game, source)
                .stream()
                .map(game::getPermanentOrLKIBattlefield)
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .max()
                .orElse(0);
        return DiscoverEffect.doDiscover(player, power, game, source) != null;
    }
}
