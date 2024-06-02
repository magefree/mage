package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.ChangeATargetOfTargetSpellAbilityToSourceEffect;
import mage.abilities.effects.common.TapSourceUnlessPaysEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.ModalDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterSpell;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.filter.predicate.other.NumberOfTargetsPredicate;
import mage.target.TargetSpell;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HydroelectricSpecimen extends ModalDoubleFacedCard {

    protected static final FilterSpell filter = new FilterInstantOrSorcerySpell("instant or sorcery spell with a single target");

    static {
        filter.add(new NumberOfTargetsPredicate(1));
    }

    public HydroelectricSpecimen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.WEIRD}, "{2}{U}",
                "Hydroelectric Laboratory", new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // 1.
        // Hydroelectric Specimen
        // Creature — Weird
        this.getLeftHalfCard().setPT(new MageInt(1), new MageInt(4));

        // Flash
        this.getLeftHalfCard().addAbility(FlashAbility.getInstance());

        // When Hydroelectric Specimen enters the battlefield, you may change the target of target instant or sorcery spell with a single target to Hydroelectric Specimen.
        Effect effect = new ChangeATargetOfTargetSpellAbilityToSourceEffect()
                .setText("change the target of target instant or sorcery spell with a single target to {this}");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetSpell(filter));
        this.getLeftHalfCard().addAbility(ability);

        // 2.
        // Hydroelectric Laboratory
        // Land

        // As Hydroelectric Laboratory enters the battlefield, you may pay 3 life. If you don't, it enters the battlefield tapped.
        this.getRightHalfCard().addAbility(new AsEntersBattlefieldAbility(
                new TapSourceUnlessPaysEffect(new PayLifeCost(3)),
                "you may pay 3 life. If you don't, it enters the battlefield tapped"
        ));

        // {T}: Add {G}.
        this.getRightHalfCard().addAbility(new BlueManaAbility());
    }

    private HydroelectricSpecimen(final HydroelectricSpecimen card) {
        super(card);
    }

    @Override
    public HydroelectricSpecimen copy() {
        return new HydroelectricSpecimen(this);
    }
}