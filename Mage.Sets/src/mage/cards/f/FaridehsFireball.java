package mage.cards.f;

import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.RollDieWithResultTableEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.target.common.TargetCreatureOrPlaneswalker;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaridehsFireball extends CardImpl {

    public FaridehsFireball(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}{R}");

        // Farideh's Fireball deals 5 damage to target creature or planeswalker. Roll a d20.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreatureOrPlaneswalker());
        RollDieWithResultTableEffect effect = new RollDieWithResultTableEffect();
        this.getSpellAbility().addEffect(effect.concatBy("."));

        // 1-9 | Farideh's Fireball deals 2 damage to each player.
        effect.addTableEntry(1, 9, new DamagePlayersEffect(2));

        // 10-20 | Farideh's Fireball deals 2 damage to each opponent.
        effect.addTableEntry(10, 20, new DamagePlayersEffect(2, TargetController.OPPONENT));
    }

    private FaridehsFireball(final FaridehsFireball card) {
        super(card);
    }

    @Override
    public FaridehsFireball copy() {
        return new FaridehsFireball(this);
    }
}
