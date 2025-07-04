package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.AsTurnedFaceUpEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

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
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Enchanted creature has deathtouch and indestructible.
        Ability ability2 = new SimpleStaticAbility(new GainAbilityAttachedEffect(
                DeathtouchAbility.getInstance(), AttachmentType.AURA
        ));
        ability2.addEffect(new GainAbilityAttachedEffect(
                IndestructibleAbility.getInstance(), AttachmentType.AURA
        ).setText("and indestructible"));
        this.addAbility(ability2);

        // Morph—Sacrifice another creature.
        this.addAbility(new MorphAbility(this, new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_ANOTHER_CREATURE)));

        // As Gift of Doom is turned face up, you may attach it to a creature.
        Effect effect = new AsTurnedFaceUpEffect(new GiftOfDoomEffect(), true);
        Ability ability3 = new SimpleStaticAbility(effect);
        ability3.setWorksFaceDown(true);
        this.addAbility(ability3);
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

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent();

    static {
        filter.add(AnotherPredicate.instance);
    }

    GiftOfDoomEffect() {
        super(Outcome.Benefit);
        staticText = "you may attach it to a creature";
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
        Permanent giftOfDoom = game.getPermanent(source.getSourceId());
        if (player == null || giftOfDoom == null) {
            return false;
        }
        TargetPermanent target = new TargetPermanent(filter);
        target.withNotTarget(true);
        if (player.choose(outcome, target, source, game)
                && game.getPermanent(target.getFirstTarget()) != null
                && !game.getPermanent(target.getFirstTarget()).cantBeAttachedBy(giftOfDoom, source, game, false)) {
            game.getState().setValue("attachTo:" + giftOfDoom.getId(), target.getFirstTarget());
            game.getPermanent(target.getFirstTarget()).addAttachment(giftOfDoom.getId(), source, game);
            return true;
        }
        player.moveCardToGraveyardWithInfo(giftOfDoom, source, game, Zone.BATTLEFIELD); //no legal target
        return false;
    }
}
