package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SomberwaldStag extends CardImpl {

    public SomberwaldStag(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");
        this.subtype.add(SubType.ELK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Somberwald Stag enters the battlefield, you may have it fight target creature you don't control.
        Effect effect = new FightTargetSourceEffect();
        effect.setText("you may have it fight target creature you don't control");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, true);
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_CREATURE_YOU_DONT_CONTROL));
        this.addAbility(ability);
    }

    private SomberwaldStag(final SomberwaldStag card) {
        super(card);
    }

    @Override
    public SomberwaldStag copy() {
        return new SomberwaldStag(this);
    }
}
