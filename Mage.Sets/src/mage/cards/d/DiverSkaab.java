package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.PutOnTopOrBottomLibraryTargetEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DiverSkaab extends CardImpl {

    public DiverSkaab(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}{U}");

        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Diver Skaab exploits a creature, target creature's owner puts it on the top or bottom of their library.
        Ability ability = new ExploitCreatureTriggeredAbility(new PutOnTopOrBottomLibraryTargetEffect(
                "target creature's owner puts it on the top or bottom of their library"
        ));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private DiverSkaab(final DiverSkaab card) {
        super(card);
    }

    @Override
    public DiverSkaab copy() {
        return new DiverSkaab(this);
    }
}
