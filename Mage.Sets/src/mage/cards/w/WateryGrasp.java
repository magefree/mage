package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.WaterbendCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WateryGrasp extends CardImpl {

    public WateryGrasp(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));

        // Waterbend {5}: Enchanted creature's owner shuffles it into their library.
        this.addAbility(new SimpleActivatedAbility(new WateryGraspEffect(), new WaterbendCost(5)));
    }

    private WateryGrasp(final WateryGrasp card) {
        super(card);
    }

    @Override
    public WateryGrasp copy() {
        return new WateryGrasp(this);
    }
}

class WateryGraspEffect extends OneShotEffect {

    WateryGraspEffect() {
        super(Outcome.Benefit);
        staticText = "enchanted creature's owner shuffles it into their library";
    }

    private WateryGraspEffect(final WateryGraspEffect effect) {
        super(effect);
    }

    @Override
    public WateryGraspEffect copy() {
        return new WateryGraspEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = Optional
                .ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .orElse(null);
        if (permanent == null) {
            return false;
        }
        Player player = game.getPlayer(permanent.getOwnerId());
        return player != null && player.shuffleCardsToLibrary(permanent, game, source);
    }
}
