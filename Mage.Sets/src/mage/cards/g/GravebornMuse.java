
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledPermanent;

/**
 *
 * @author Loki
 */
public final class GravebornMuse extends CardImpl {
    private static FilterControlledPermanent filter = new FilterControlledPermanent("Zombie you control");

    static {
        filter.add(SubType.ZOMBIE.getPredicate());
    }

    public GravebornMuse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SPIRIT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your upkeep, you draw X cards and you lose X life, where X is the number of Zombies you control.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new DrawCardSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("you draw X cards"), TargetController.YOU, false);
        ability.addEffect(new LoseLifeSourceControllerEffect(new PermanentsOnBattlefieldCount(filter))
                .setText("and you lose X life, where X is the number of Zombies you control"));
        this.addAbility(ability);
    }

    private GravebornMuse(final GravebornMuse card) {
        super(card);
    }

    @Override
    public GravebornMuse copy() {
        return new GravebornMuse(this);
    }
}
