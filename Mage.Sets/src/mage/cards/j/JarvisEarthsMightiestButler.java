package mage.cards.j;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterSpell;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class JarvisEarthsMightiestButler extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Hero spell");

    static {
        filter.add(SubType.HERO.getPredicate());
    }

    public JarvisEarthsMightiestButler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ADVISOR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever you cast a Hero spell, draw a card.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new DrawCardSourceControllerEffect(1), filter, false
        ));
    }

    private JarvisEarthsMightiestButler(final JarvisEarthsMightiestButler card) {
        super(card);
    }

    @Override
    public JarvisEarthsMightiestButler copy() {
        return new JarvisEarthsMightiestButler(this);
    }
}
