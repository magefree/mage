
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.CreaturesCantGetOrHaveAbilityEffect;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.ShroudAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class ArcaneLighthouse extends CardImpl {

    public ArcaneLighthouse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());

        // {1}, {tap}: Until end of turn, creatures your opponents control lose hexproof and shroud and can't have hexproof or shroud.
        Effect effect = new CreaturesCantGetOrHaveAbilityEffect(HexproofAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("Until end of turn, creatures your opponents control lose hexproof");
        Ability ability = new SimpleActivatedAbility(effect, new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        effect = new CreaturesCantGetOrHaveAbilityEffect(ShroudAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and shroud and can't have hexproof or shroud");
        ability.addEffect(effect);
        this.addAbility(ability);

    }

    private ArcaneLighthouse(final ArcaneLighthouse card) {
        super(card);
    }

    @Override
    public ArcaneLighthouse copy() {
        return new ArcaneLighthouse(this);
    }
}
