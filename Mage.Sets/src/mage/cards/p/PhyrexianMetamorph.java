package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.CopyPermanentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.util.functions.CopyApplier;

/**
 *
 * @author Loki
 */
public final class PhyrexianMetamorph extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("artifact or creature");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                CardType.CREATURE.getPredicate()));
    }

    public PhyrexianMetamorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}{U/P}");
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.SHAPESHIFTER);

        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        CopyApplier phyrexianMetamorphCopyApplier = new CopyApplier() {
            @Override
            public boolean apply(Game game, MageObject blueprint, Ability source, UUID copyToObjectId) {
                blueprint.addCardType(CardType.ARTIFACT);
                return true;
            }
        };

        // {U/P} ( can be paid with either {U} or 2 life.)
        // You may have Phyrexian Metamorph enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types.
        Effect effect = new CopyPermanentEffect(filter, phyrexianMetamorphCopyApplier);
        effect.setText("You may have {this} enter the battlefield as a copy of any artifact or creature on the battlefield, except it's an artifact in addition to its other types");
        Ability ability = new SimpleStaticAbility(Zone.ALL, new EntersBattlefieldEffect(effect, "", true));
        this.addAbility(ability);
    }

    private PhyrexianMetamorph(final PhyrexianMetamorph card) {
        super(card);
    }

    @Override
    public PhyrexianMetamorph copy() {
        return new PhyrexianMetamorph(this);
    }

}
