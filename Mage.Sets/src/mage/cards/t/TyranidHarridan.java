package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToAPlayerAllTriggeredAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.WardAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.FilterPermanentThisOrAnother;
import mage.game.permanent.token.TyranidGargoyleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TyranidHarridan extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanentThisOrAnother(
            new FilterPermanent(SubType.TYRANID, "Tyranid you control"), true
    );

    public TyranidHarridan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{U}");

        this.subtype.add(SubType.TYRANID);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Ward {4}
        this.addAbility(new WardAbility(new GenericManaCost(4), false));

        // Shrieking Gargoyles -- Whenever Tyranid Harridan or another Tyranid you control deals combat damage to a player, create a 1/1 blue Tyranid Gargoyle creature token with flying.
        Ability ability = new DealsDamageToAPlayerAllTriggeredAbility(
                new CreateTokenEffect(new TyranidGargoyleToken()),
                filter, false, SetTargetPointer.NONE, true
        );
        ability.withFlavorWord("Shrieking Gargoyles");
        this.addAbility(ability);
    }

    private TyranidHarridan(final TyranidHarridan card) {
        super(card);
    }

    @Override
    public TyranidHarridan copy() {
        return new TyranidHarridan(this);
    }
}