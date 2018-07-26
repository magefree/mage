package mage.cards.g;


import mage.abilities.effects.Effect;
import mage.abilities.effects.common.combat.CanBlockAdditionalCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 *
 * @author EikePeace
 */
public final class Grit extends CardImpl {

    public Grit(UUID ownerId, CardSetInfo setInfo){
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance(), Duration.EndOfTurn));
        Effect effect = new CanBlockAdditionalCreatureTargetEffect(Duration.EndOfTurn, 0);
        effect.setText("and can block any number of creatures this turn.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    public Grit(final Grit card){super(card);}

    @Override
    public Grit copy(){return new Grit(this);}
}
