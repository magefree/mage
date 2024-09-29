package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.effects.common.discard.DiscardTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX
 */
public final class BlackCat extends CardImpl {

    public BlackCat(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{B}");
        this.subtype.add(SubType.ZOMBIE, SubType.CAT);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // When Black Cat dies, target opponent discards a card at random.
        Ability ability = new DiesSourceTriggeredAbility(new DiscardTargetEffect(1, true),false);
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private BlackCat(final BlackCat card) {
        super(card);
    }

    @Override
    public BlackCat copy() {
        return new BlackCat(this);
    }
}
