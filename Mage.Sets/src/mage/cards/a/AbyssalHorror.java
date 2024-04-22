

package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;

/**
 *
 * @author Backfir3
 */
public final class AbyssalHorror extends CardImpl {

    public AbyssalHorror (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}{B}");
        this.subtype.add(SubType.HORROR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        this.addAbility(FlyingAbility.getInstance());

        Ability ability = new EntersBattlefieldTriggeredAbility(new DiscardTargetEffect(2));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);
    }

    private AbyssalHorror(final AbyssalHorror card) {
        super(card);
    }

    @Override
    public AbyssalHorror copy() {
        return new AbyssalHorror(this);
    }

}
