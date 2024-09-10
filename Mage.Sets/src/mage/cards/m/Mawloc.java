package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ExileTargetIfDiesEffect;
import mage.abilities.effects.common.FightTargetSourceEffect;
import mage.abilities.keyword.RavenousAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponentsCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Mawloc extends CardImpl {

    public Mawloc(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{X}{R}{G}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ravenous
        this.addAbility(new RavenousAbility());

        // Terror from the Deep -- When Mawloc enters the battlefield, it fights up to one target creature an opponent controls. If that creature would die this turn, exile it instead.
        Ability ability = new EntersBattlefieldTriggeredAbility(new FightTargetSourceEffect()
                .setText("it fights up to one target creature an opponent controls"));
        ability.addEffect(new ExileTargetIfDiesEffect());
        ability.addTarget(new TargetOpponentsCreaturePermanent(0, 1));
        this.addAbility(ability.withFlavorWord("Terror from the Deep"));
    }

    private Mawloc(final Mawloc card) {
        super(card);
    }

    @Override
    public Mawloc copy() {
        return new Mawloc(this);
    }
}
