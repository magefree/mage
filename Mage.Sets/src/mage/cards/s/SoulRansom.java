
package mage.cards.s;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.DiscardTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.ControlEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInHand;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class SoulRansom extends CardImpl {

    public SoulRansom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{B}");
        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.GainControl));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // You control enchanted creature.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ControlEnchantedEffect()));

        // Discard two cards: Soul Ransom's controller sacrifices it, then draws two cards. Only any opponent may activate this ability.
        SimpleActivatedAbility ability2 = new SimpleActivatedAbility(
                Zone.BATTLEFIELD,
                new SoulRansomEffect(),
                new DiscardTargetCost(
                        new TargetCardInHand(2, 2, new FilterCard("two cards"))
                )
        );
        ability2.setMayActivate(TargetController.OPPONENT);
        this.addAbility(ability2);

    }

    private SoulRansom(final SoulRansom card) {
        super(card);
    }

    @Override
    public SoulRansom copy() {
        return new SoulRansom(this);
    }
}

class SoulRansomEffect extends OneShotEffect {

    SoulRansomEffect() {
        super(Outcome.Benefit);
        this.staticText = "{this}'s controller sacrifices it, then draws two cards. Only any opponent may activate this ability";
    }

    SoulRansomEffect(final SoulRansomEffect effect) {
        super(effect);
    }

    @Override
    public SoulRansomEffect copy() {
        return new SoulRansomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent != null) {
            permanent.sacrifice(source, game);
        } else {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (permanent == null) {
            return false;
        }
        Player controller = game.getPlayer(permanent.getControllerId());
        if (controller == null) {
            return false;
        }
        controller.drawCards(2, source, game);
        return true;
    }
}
