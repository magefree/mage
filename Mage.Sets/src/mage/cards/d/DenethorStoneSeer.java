package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class DenethorStoneSeer extends CardImpl {

    public DenethorStoneSeer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // When Denethor, Stone Seer enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // {3}{R}, {T}, Sacrifice Denethor: Target player becomes the monarch. Denethor deals 3 damage to any target.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new DenethorStoneSeerEffect(),
                new ManaCostsImpl<>("{3}{R}")
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetPlayer());
        ability.addTarget(new TargetAnyTarget());

        this.addAbility(ability);
    }

    private DenethorStoneSeer(final DenethorStoneSeer card) {
        super(card);
    }

    @Override
    public DenethorStoneSeer copy() {
        return new DenethorStoneSeer(this);
    }
}

class DenethorStoneSeerEffect extends OneShotEffect {

    DenethorStoneSeerEffect() {
        super(Outcome.Benefit);
        staticText = "Target player becomes the monarch. {this} deals 3 damage to any target.";
    }

    private DenethorStoneSeerEffect(final DenethorStoneSeerEffect effect) {
        super(effect);
    }

    @Override
    public DenethorStoneSeerEffect copy() {
        return new DenethorStoneSeerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getTargets().get(0).getFirstTarget());
        if (player != null) {
            game.setMonarchId(source, player.getId());
        }
        game.damagePlayerOrPermanent(
                source.getTargets().get(1).getFirstTarget(), 3,
                source.getSourceId(), source, game, false, true
        );
        return true;
    }
}