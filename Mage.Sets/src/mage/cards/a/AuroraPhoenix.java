package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToHandEffect;
import mage.abilities.keyword.CascadeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterSpell;
import mage.filter.predicate.mageobject.AbilityPredicate;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AuroraPhoenix extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("a spell with cascade");

    static {
        filter.add(new AbilityPredicate(CascadeAbility.class));
    }

    public AuroraPhoenix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}{R}");

        this.subtype.add(SubType.PHOENIX);
        this.power = new MageInt(5);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Cascade
        this.addAbility(new CascadeAbility());

        // Whenever you cast a spell with cascade, return Aurora Phoenix from your graveyard to your hand.
        this.addAbility(new SpellCastControllerTriggeredAbility(
                Zone.GRAVEYARD, new ReturnSourceFromGraveyardToHandEffect(),
                filter, false, SetTargetPointer.NONE
        ));
    }

    private AuroraPhoenix(final AuroraPhoenix card) {
        super(card);
    }

    @Override
    public AuroraPhoenix copy() {
        return new AuroraPhoenix(this);
    }
}
