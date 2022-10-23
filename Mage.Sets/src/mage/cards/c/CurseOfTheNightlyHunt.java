
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RequirementEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 *
 * @author BetaSteward
 */
public final class CurseOfTheNightlyHunt extends CardImpl {

    public CurseOfTheNightlyHunt(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}");
        this.subtype.add(SubType.AURA, SubType.CURSE);


        // Enchant player
        TargetPlayer auraTarget = new TargetPlayer();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.Detriment));
        this.addAbility(new EnchantAbility(auraTarget));

        // Creatures enchanted player controls attack each turn if able.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CurseOfTheNightlyHuntEffect()));

    }

    private CurseOfTheNightlyHunt(final CurseOfTheNightlyHunt card) {
        super(card);
    }

    @Override
    public CurseOfTheNightlyHunt copy() {
        return new CurseOfTheNightlyHunt(this);
    }
}

class CurseOfTheNightlyHuntEffect extends RequirementEffect {

    public CurseOfTheNightlyHuntEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures enchanted player controls attack each turn if able";
    }

    public CurseOfTheNightlyHuntEffect(final CurseOfTheNightlyHuntEffect effect) {
        super(effect);
    }

    @Override
    public CurseOfTheNightlyHuntEffect copy() {
        return new CurseOfTheNightlyHuntEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null && enchantment.getAttachedTo() != null) {
            if (permanent.isControlledBy(enchantment.getAttachedTo())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean mustAttack(Game game) {
        return true;
    }

    @Override
    public boolean mustBlock(Game game) {
        return false;
    }

}
