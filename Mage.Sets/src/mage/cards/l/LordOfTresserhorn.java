
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DrawCardTargetEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class LordOfTresserhorn extends CardImpl {

    public LordOfTresserhorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{U}{B}{R}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.ZOMBIE);

        this.power = new MageInt(10);
        this.toughness = new MageInt(4);

        // When Lord of Tresserhorn enters the battlefield, you lose 2 life, you sacrifice two creatures, and target opponent draws two cards.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeSourceControllerEffect(2), false);
        ability.addEffect(new SacrificeControllerEffect(new FilterControlledCreaturePermanent("creatures"), 2, "you"));
        Effect effect = new DrawCardTargetEffect(2);
        effect.setText(", and target opponent draws two cards");
        ability.addEffect(effect);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {B}: Regenerate Lord of Tresserhorn.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ManaCostsImpl<>("{B}")));
    }

    private LordOfTresserhorn(final LordOfTresserhorn card) {
        super(card);
    }

    @Override
    public LordOfTresserhorn copy() {
        return new LordOfTresserhorn(this);
    }
}
