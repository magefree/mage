package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.PermanentPredicate;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;

/**
 *
 * @author Xanderhall
 */
public final class ArchmageOfEchoes extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a Faerie or Wizard permanent spell");

    static {
        filter.add(PermanentPredicate.instance);
        filter.add(Predicates.or(SubType.FAERIE.getPredicate(), SubType.WIZARD.getPredicate()));
    }

    public ArchmageOfEchoes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");
        
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {2}
        this.addAbility(new WardAbility(new ManaCostsImpl<>("{2}"), false));

        // Whenever you cast a Faerie or Wizard permanent spell, copy it.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                new CopyTargetSpellEffect(false, false, false).setText("copy it"),
                filter, false, SetTargetPointer.SPELL));
    }

    private ArchmageOfEchoes(final ArchmageOfEchoes card) {
        super(card);
    }

    @Override
    public ArchmageOfEchoes copy() {
        return new ArchmageOfEchoes(this);
    }
}
