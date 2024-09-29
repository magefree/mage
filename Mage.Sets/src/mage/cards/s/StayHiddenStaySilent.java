package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepEnchantedEffect;
import mage.abilities.effects.common.TapEnchantedEffect;
import mage.abilities.effects.keyword.ManifestDreadEffect;
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
public final class StayHiddenStaySilent extends CardImpl {

    public StayHiddenStaySilent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // When Stay Hidden, Stay Silent enters, tap enchanted creature.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new TapEnchantedEffect()));

        // Enchanted creature doesn't untap during its controller's untap step.
        this.addAbility(new SimpleStaticAbility(new DontUntapInControllersUntapStepEnchantedEffect()));

        // {4}{U}{U}: Shuffle enchanted creature into its owner's library, then manifest dread. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new StayHiddenStaySilentEffect(), new ManaCostsImpl<>("{4}{U}{U}")
        );
        ability.addEffect(new ManifestDreadEffect().concatBy(", then"));
        this.addAbility(ability);
    }

    private StayHiddenStaySilent(final StayHiddenStaySilent card) {
        super(card);
    }

    @Override
    public StayHiddenStaySilent copy() {
        return new StayHiddenStaySilent(this);
    }
}

class StayHiddenStaySilentEffect extends OneShotEffect {

    StayHiddenStaySilentEffect() {
        super(Outcome.Benefit);
        staticText = "shuffle enchanted creature into its owner's library";
    }

    private StayHiddenStaySilentEffect(final StayHiddenStaySilentEffect effect) {
        super(effect);
    }

    @Override
    public StayHiddenStaySilentEffect copy() {
        return new StayHiddenStaySilentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Optional.ofNullable(source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(game::getPermanent)
                .ifPresent(permanent -> {
                    Player player = game.getPlayer(permanent.getControllerId());
                    if (player != null) {
                        player.shuffleCardsToLibrary(permanent, game, source);
                    }
                });
        return true;
    }
}
