
package mage.cards.n;

import java.util.UUID;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;

/**
 * @author Loki
 */
public final class NornsAnnex extends CardImpl {

    public NornsAnnex(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}{W/P}{W/P}");

        // {W/P} ({W/P} can be paid with either or 2 life.)
        // Creatures can't attack you or a planeswalker you control unless their controller pays {W/P} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(new CantAttackYouUnlessPayAllEffect(
                new ManaCostsImpl<>("{W/P}"), true
        ).setText("creatures can't attack you or planeswalkers you control unless their controller pays {W/P} for each of those creatures")));
    }

    private NornsAnnex(final NornsAnnex card) {
        super(card);
    }

    @Override
    public NornsAnnex copy() {
        return new NornsAnnex(this);
    }

}
