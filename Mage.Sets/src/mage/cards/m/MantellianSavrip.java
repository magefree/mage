package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesWithLessPowerEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MantellianSavrip extends CardImpl {

    public MantellianSavrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.MANTELLIAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {5}{G}{G}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{5}{G}{G}", 2));

        // Creatures with power less than Mantellian Savrip's power can't block it.
        this.addAbility(new SimpleStaticAbility(
            Zone.BATTLEFIELD,
            new CantBeBlockedByCreaturesWithLessPowerEffect()
        ));
    }

    private MantellianSavrip(final MantellianSavrip card) {
        super(card);
    }

    @Override
    public MantellianSavrip copy() {
        return new MantellianSavrip(this);
    }
}