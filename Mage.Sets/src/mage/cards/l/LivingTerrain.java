
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.continuous.BecomesCreatureAttachedEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class LivingTerrain extends CardImpl {

    public LivingTerrain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{G}{G}");
        this.subtype.add(SubType.AURA);


        // Enchant land
        
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.PutCreatureInPlay));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);
        // Enchanted land is a 5/6 green Treefolk creature that's still a land.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BecomesCreatureAttachedEffect(
                new TreefolkToken(), "Enchanted land is a 5/6 green Treefolk creature that's still a land", Duration.WhileOnBattlefield, BecomesCreatureAttachedEffect.LoseType.COLOR)));
    }

    private LivingTerrain(final LivingTerrain card) {
        super(card);
    }

    @Override
    public LivingTerrain copy() {
        return new LivingTerrain(this);
    }
}

class TreefolkToken extends TokenImpl {
        TreefolkToken() {
            super("Treefolk", "5/6 green Treefolk creature");
            cardType.add(CardType.CREATURE);
            this.color.setGreen(true);
            subtype.add(SubType.TREEFOLK);
            power = new MageInt(5);
            toughness = new MageInt(6);
        }
    public TreefolkToken(final TreefolkToken token) {
        super(token);
    }

    public TreefolkToken copy() {
        return new TreefolkToken(this);
    }
    }
