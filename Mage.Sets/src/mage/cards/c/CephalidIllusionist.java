
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class CephalidIllusionist extends CardImpl {

    public CephalidIllusionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}");
        this.subtype.add(SubType.CEPHALID);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Cephalid Illusionist becomes the target of a spell or ability, put the top three cards of your library into your graveyard.
        this.addAbility(new BecomesTargetTriggeredAbility(new MillCardsControllerEffect(3)));
        
        // {2}{U}, {tap}: Prevent all combat damage that would be dealt to
        Effect effect = new PreventDamageToTargetEffect(Duration.EndOfTurn, true);
        effect.setText("Prevent all combat damage that would be dealt to");
        // and dealt by target creature you control this turn.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, effect, new ManaCostsImpl<>("{2}{U}"));
        effect = new PreventDamageByTargetEffect(Duration.EndOfTurn, true);
        effect.setText("and dealt by target creature you control this turn.");
        ability.addEffect(effect);
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private CephalidIllusionist(final CephalidIllusionist card) {
        super(card);
    }

    @Override
    public CephalidIllusionist copy() {
        return new CephalidIllusionist(this);
    }
}
