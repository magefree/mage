package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.VariableManaCostPredicate;

/**
 *
 * @author muz
 */
public final class OwlinSpiralmancer extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell with {X} in its mana cost");

    static {
        filter.add(VariableManaCostPredicate.instance);
    }

    public OwlinSpiralmancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever you cast your first spell with {X} in its mana cost each turn, you may copy it. You may choose new targets for the copy.
        this.addAbility(new SpellCastControllerTriggeredAbility(
            new CopyStackObjectEffect(), filter, true, SetTargetPointer.SPELL
        ).setTriggersLimitEachTurn(1));
    }

    private OwlinSpiralmancer(final OwlinSpiralmancer card) {
        super(card);
    }

    @Override
    public OwlinSpiralmancer copy() {
        return new OwlinSpiralmancer(this);
    }
}
