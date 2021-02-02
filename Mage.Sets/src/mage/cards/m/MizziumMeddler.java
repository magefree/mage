
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.ChangeATargetOfTargetSpellAbilityToSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetStackObject;

/**
 *
 * @author fireshoes
 */
public final class MizziumMeddler extends CardImpl {

    public MizziumMeddler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.VEDALKEN);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // When Mizzium Meddler enters the battlefield, you may change a target of target spell or ability to Mizzium Meddler.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ChangeATargetOfTargetSpellAbilityToSourceEffect(), true);
        ability.addTarget(new TargetStackObject());
        this.addAbility(ability);
    }

    private MizziumMeddler(final MizziumMeddler card) {
        super(card);
    }

    @Override
    public MizziumMeddler copy() {
        return new MizziumMeddler(this);
    }
}
