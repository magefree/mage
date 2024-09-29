package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalevolentChandelier extends CardImpl {

    public MalevolentChandelier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // {2}: Put target card from a graveyard on the bottom of its owner's library. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new PutOnLibraryTargetEffect(false), new GenericManaCost(2)
        );
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private MalevolentChandelier(final MalevolentChandelier card) {
        super(card);
    }

    @Override
    public MalevolentChandelier copy() {
        return new MalevolentChandelier(this);
    }
}
