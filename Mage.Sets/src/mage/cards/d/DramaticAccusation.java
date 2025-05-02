package mage.cards.d;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
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
public final class DramaticAccusation extends CardImpl {

    public DramaticAccusation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Dramatic Accusation enters the battlefield, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));

        // {U}{U}: Shuffle enchanted creature into its owner's library.
        this.addAbility(new SimpleActivatedAbility(new DramaticAccusationEffect(), new ManaCostsImpl<>("{U}{U}")));
    }

    private DramaticAccusation(final DramaticAccusation card) {
        super(card);
    }

    @Override
    public DramaticAccusation copy() {
        return new DramaticAccusation(this);
    }
}

class DramaticAccusationEffect extends OneShotEffect {

    DramaticAccusationEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle enchanted creature into its owner's library";
    }

    private DramaticAccusationEffect(final DramaticAccusationEffect effect) {
        super(effect);
    }

    @Override
    public DramaticAccusationEffect copy() {
        return new DramaticAccusationEffect(this);
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
        Player owner = game.getPlayer(permanent.getOwnerId());
        return owner != null && owner.shuffleCardsToLibrary(permanent, game, source);
    }
}
