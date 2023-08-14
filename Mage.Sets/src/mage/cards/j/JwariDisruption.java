package mage.cards.j;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CounterUnlessPaysEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author JayDi85
 */
public final class JwariDisruption extends ModalDoubleFacedCard {

    public JwariDisruption(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.INSTANT}, new SubType[]{}, "{1}{U}",
                "Jwari Ruins", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Jwari Disruption
        // Instant

        // Counter target spell unless its controller pays {1}.
        this.getLeftHalfCard().getSpellAbility().addEffect(new CounterUnlessPaysEffect(new GenericManaCost(1)));
        this.getLeftHalfCard().getSpellAbility().addTarget(new TargetSpell());

        // 2.
        // Jwari Ruins
        // Land

        // Jwari Ruins enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private JwariDisruption(final JwariDisruption card) {
        super(card);
    }

    @Override
    public JwariDisruption copy() {
        return new JwariDisruption(this);
    }
}
