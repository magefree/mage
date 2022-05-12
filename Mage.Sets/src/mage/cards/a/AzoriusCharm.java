
package mage.cards.a;

import java.util.UUID;
import mage.abilities.Mode;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetAttackingOrBlockingCreature;

/**
 *
 * @author LevelX2
 */
public final class AzoriusCharm extends CardImpl {

    public AzoriusCharm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}{U}");


        // Choose one — Creatures you control gain lifelink until end of turn;
        this.getSpellAbility().addEffect(new GainAbilityControlledEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn, new FilterControlledCreaturePermanent("Creatures")));

        // or draw a card;
        Mode mode = new Mode(new DrawCardSourceControllerEffect(1));
        this.getSpellAbility().addMode(mode);

        // or put target attacking or blocking creature on top of its owner's library.
        mode = new Mode(new PutOnLibraryTargetEffect(true));
        mode.addTarget(new TargetAttackingOrBlockingCreature());
        this.getSpellAbility().addMode(mode);
    }

    private AzoriusCharm(final AzoriusCharm card) {
        super(card);
    }

    @Override
    public AzoriusCharm copy() {
        return new AzoriusCharm(this);
    }
}

