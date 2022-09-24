package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.keyword.SpliceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class Evermind extends CardImpl {

    public Evermind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"");
        this.subtype.add(SubType.ARCANE);

        this.color.setBlue(true);

        // <i>(Nonexistent mana costs can't be paid.)</i>
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, new InfoEffect("<i>(Nonexistent mana costs can't be paid.)</i>"));
        ability.setRuleAtTheTop(true);
        this.addAbility(ability);

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1));
        
        // Splice onto Arcane {1}{U}
        this.addAbility(new SpliceAbility(SpliceAbility.ARCANE, "{1}{U}"));
    }

    private Evermind(final Evermind card) {
        super(card);
    }

    @Override
    public Evermind copy() {
        return new Evermind(this);
    }
}
