package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AttachmentType;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

import static mage.constants.Outcome.Benefit;

/**
 * @author TheElk801
 */
public final class GiftOfDoom extends CardImpl {

    public GiftOfDoom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget.getTargetName());
        this.addAbility(ability);

        // Enchanted creature has deathtouch and indestructible.
        ability = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.AURA
        ));
        ability.addEffect(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.AURA
        ));
        this.addAbility(ability);

        // Morph—Sacrifice another creature.
        this.addAbility(new MorphAbility(this, new SacrificeTargetCost(
                new TargetControlledPermanent(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)
        )));

        // As Gift of Doom is turned face up, you may attach it to a creature.
        this.addAbility(new SimpleStaticAbility(new AsTurnedFaceUpEffect(new GiftOfDoomEffect(), true)));
    }

    private GiftOfDoom(final GiftOfDoom card) {
        super(card);
    }

    @Override
    public GiftOfDoom copy() {
        return new GiftOfDoom(this);
    }
}

class GiftOfDoomEffect extends OneShotEffect {

    GiftOfDoomEffect() {
        super(Benefit);
        staticText = "attach it to a creature";
    }

    private GiftOfDoomEffect(final GiftOfDoomEffect effect) {
        super(effect);
    }

    @Override
    public GiftOfDoomEffect copy() {
        return new GiftOfDoomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (player == null || permanent == null) {
            return false;
        }
        TargetCreaturePermanent target = new TargetCreaturePermanent(0, 1);
        target.setNotTarget(true);
        if (!player.choose(outcome, target, source.getSourceId(), game)) {
            return false;
        }
        permanent.attachTo(target.getFirstTarget(), game);
        return true;
    }
}